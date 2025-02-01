# ===== Import delle librerie =====
import pandas as pd
import numpy as np
from sklearn.ensemble import RandomForestRegressor
from sklearn.preprocessing import LabelEncoder
from flask import Flask, request, jsonify
import ast

# ===== 1. Caricamento del dataset =====
dataset = None

def load_dataset_once():
    global dataset
    if dataset is None:
        dataset = pd.read_csv('dataset_outfits.csv')
    return dataset

# ===== 2. Pre-elaborazione dei dati =====
def preprocess_data(df, label_encoder, is_user_data=False, temperature=None):
    categorical_columns = ['top_category', 'bottom_category', 'shoes_category', 'top_material', 'bottom_material', 'shoes_material']
    for col in categorical_columns:
        df[col] = df[col].fillna('Unknown').astype(str)
        df[col] = df[col].apply(lambda x: safe_transform(x, label_encoder))

    for col in ['top_color', 'bottom_color', 'shoes_color']:
        df[col] = df[col].apply(lambda c: ast.literal_eval(c) if isinstance(c, str) else c)

    for item in ['top', 'bottom', 'shoes']:
        df[f'{item}_color_code'] = df[f'{item}_color'].apply(lambda c: c[0] * 0.299 + c[1] * 0.587 + c[2] * 0.114)

    df['temperature'] = temperature if is_user_data else np.nan
    return df


def safe_transform(value, label_encoder):
    if isinstance(value, str):
        if value not in label_encoder.classes_:
            if 'Unknown' not in label_encoder.classes_:
                label_encoder.classes_ = np.append(label_encoder.classes_, 'Unknown')
            return label_encoder.transform(['Unknown'])[0]
        return label_encoder.transform([value])[0]
    return value

# ===== 3. Filtro basato su regole =====
def filter_by_weather(df, label_encoder, temperature, weather_condition):
    # Se la temperatura è superiore ai 30 gradi, mantieni solo capi leggeri
    if temperature > 30:
        df = df[df['top_category'].apply(lambda x: label_encoder.inverse_transform([x])[0] in ['T-shirt', 'Tank Top'])]
        df = df[df['bottom_category'].apply(lambda x: label_encoder.inverse_transform([x])[0] in ['Shorts', 'Skirt'])]

    # Se la temperatura è tra i 20 e i 25 gradi, mantieni capi leggeri ma adatti a temperature moderate
    if 20 < temperature <= 25:
        df = df[df['top_category'].apply(lambda x: label_encoder.inverse_transform([x])[0] in ['T-shirt', 'Tank Top', 'Shirt'])]
        df = df[df['bottom_category'].apply(lambda x: label_encoder.inverse_transform([x])[0] in ['Shorts', 'Jeans', 'Chinos', 'Skirt', 'Culottes'])]

    # Se la temperatura è inferiore a 10 gradi, mantieni capi molto pesanti come giacche e piumini
    if temperature < 10:
        df = df[df['top_category'].apply(lambda x: label_encoder.inverse_transform([x])[0] in ["Hoodie", "Blazer", "Sweater", "Jacket", "Coat", "Vest", "Cardigan", "Puffer"])]
        df = df[df['bottom_category'].apply(lambda x: label_encoder.inverse_transform([x])[0] in ['Jeans', 'Trousers', 'Chinos', 'Sweatpants', 'Cargo Pants', 'Joggers'])]

    # Se la temperatura è inferiore ai 20 gradi, mantieni top e pantaloni più pesanti
    if 10 <= temperature < 20:
        df = df[df['top_category'].apply(lambda x: label_encoder.inverse_transform([x])[0] in ["Hoodie", "Blazer", "Sweater", "Jacket", "Coat", "Vest", "Cardigan"])]
        df = df[df['bottom_category'].apply(lambda x: label_encoder.inverse_transform([x])[0] in ['Jeans', 'Trousers', 'Chinos', 'Sweatpants', 'Cargo Pants', 'Joggers', 'Leggings'])]

    # Se la condizione meteo è pioggia, escludi materiali come suede e pelle
    if weather_condition == 'rain':
        df = df[df['top_material'].apply(lambda x: label_encoder.inverse_transform([x])[0] != 'Suede')]
        df = df[df['bottom_material'].apply(lambda x: label_encoder.inverse_transform([x])[0] != 'Suede')]
        df = df[df['shoes_material'].apply(lambda x: label_encoder.inverse_transform([x])[0] != 'Suede')]
        df = df[df['shoes_material'].apply(lambda x: label_encoder.inverse_transform([x])[0] != 'Leather')]

        # Escludi anche materiali come nylon e velluto che non sono ideali in caso di pioggia
        df = df[df['top_material'].apply(lambda x: label_encoder.inverse_transform([x])[0] != 'Nylon')]
        df = df[df['bottom_material'].apply(lambda x: label_encoder.inverse_transform([x])[0] != 'Nylon')]
        df = df[df['shoes_material'].apply(lambda x: label_encoder.inverse_transform([x])[0] != 'Nylon')]

        df = df[df['top_material'].apply(lambda x: label_encoder.inverse_transform([x])[0] != 'Velvet')]
        df = df[df['bottom_material'].apply(lambda x: label_encoder.inverse_transform([x])[0] != 'Velvet')]
        df = df[df['shoes_material'].apply(lambda x: label_encoder.inverse_transform([x])[0] != 'Velvet')]

    return df

# ===== 4. Modello ML per il punteggio =====
def train_outfit_model(df):
    feature_cols = ['top_category', 'top_material', 'top_color_code',
                    'bottom_category', 'bottom_material', 'bottom_color_code',
                    'shoes_category', 'shoes_material', 'shoes_color_code',
                    'temperature']
    X = df[feature_cols]
    y = df['score'] if 'score' in df.columns else np.random.uniform(5, 10, len(df))
    model = RandomForestRegressor()
    model.fit(X, y)
    return model

# ===== 5. Generazione degli outfit =====
def generate_outfits(user_data):
    outfit_combinations = [
        {**top, **bottom, **shoes}
        for top in user_data['tops']
        for bottom in user_data['bottoms']
        for shoes in user_data['shoes']
    ]
    return outfit_combinations

# ===== 6. Valutazione degli outfit =====
def evaluate_outfits(outfits, model, label_encoder, temperature):
    scored_outfits = []
    for outfit in outfits:
        features = [
            safe_transform(outfit['top_category'], label_encoder),
            safe_transform(outfit['top_material'], label_encoder),
            outfit['top_color_code'],
            safe_transform(outfit['bottom_category'], label_encoder),
            safe_transform(outfit['bottom_material'], label_encoder),
            outfit['bottom_color_code'],
            safe_transform(outfit['shoes_category'], label_encoder),
            safe_transform(outfit['shoes_material'], label_encoder),
            outfit['shoes_color_code'],
            temperature
        ]
        score = model.predict([features])[0]
        scored_outfits.append((outfit, score))
    return sorted(scored_outfits, key=lambda x: x[1], reverse=True)

def decode_outfit(outfit, label_encoder):
    """Converte i valori numerici dell'outfit nei nomi originali."""
    decoded_outfit = {}

    for key in outfit.keys():
        if key in ['top_category', 'bottom_category', 'shoes_category', 'top_material', 'bottom_material', 'shoes_material']:
            try:
                print(f"DEBUG - Valore numerico prima della decodifica ({key}): {outfit[key]}")
                decoded_value = label_encoder.inverse_transform([outfit[key]])[0]
                decoded_outfit[key] = decoded_value
            except ValueError:
                print(f"ERRORE - Valore {outfit[key]} non trovato nel LabelEncoder!")
                decoded_outfit[key] = 'Unknown'
        else:
            decoded_outfit[key] = outfit[key]

    print(f"DEBUG - Outfit decodificato: {decoded_outfit}")
    return decoded_outfit

# ===== 7. Integrazione con Flask =====
app = Flask(__name__)

@app.route('/generate_outfit', methods=['POST'])
def generate_outfit_api():
    data = request.json
    if 'user_items' not in data or 'temperature' not in data or 'weather_condition' not in data:
        return jsonify({"error": "Dati mancanti."}), 400

    user_items = data['user_items']
    user_data = generate_outfits(user_items)
    user_df = pd.DataFrame(user_data)

    training_data = load_dataset_once()
    all_categories = pd.concat([
                                   training_data[col] for col in ['top_category', 'bottom_category', 'shoes_category', 'top_material', 'bottom_material', 'shoes_material']
                               ] + [
                                   user_df[col] for col in ['top_category', 'bottom_category', 'shoes_category', 'top_material', 'bottom_material', 'shoes_material']
                               ]).astype(str)

    label_encoder = LabelEncoder()
    label_encoder.fit(np.append(all_categories, 'Unknown'))

    training_data = preprocess_data(training_data, label_encoder)
    user_df = preprocess_data(user_df, label_encoder, is_user_data=True, temperature=data['temperature'])
    filtered_items = filter_by_weather(user_df, label_encoder, data['temperature'], data['weather_condition'])

    model = train_outfit_model(training_data)
    scored_outfits = evaluate_outfits(filtered_items.to_dict(orient='records'), model, label_encoder, data['temperature'])

    decoded_outfits = [{"outfit": decode_outfit(o[0], label_encoder), "score": o[1]} for o in scored_outfits[:3]]
    return jsonify(decoded_outfits)

if __name__ == "__main__":
    app.run()
