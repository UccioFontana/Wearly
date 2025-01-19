import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.ensemble import RandomForestClassifier
from sklearn.preprocessing import OneHotEncoder
from sklearn.compose import ColumnTransformer
from sklearn.pipeline import Pipeline

# Esempio di dataset (può essere importato da un file CSV)
data = {
    'Tipo Capi': ['Giacca', 'Maglietta', 'Camicia'],
    'Colore': ['Blu', 'Bianco', 'Nero'],
    'Temperatura': [5, 22, 18],
    'Condizione': ['Pioggia', 'Soleggiato', 'Nuvoloso'],
    'Stile': ['Elegante', 'Casual', 'Vintage'],
    'Outfit Generato': ['Giacca + Pantaloni', 'Maglietta + Jeans', 'Camicia + Giacca']
}

df = pd.DataFrame(data)

# Codifica one-hot per variabili categoriche
X = df.drop('Outfit Generato', axis=1)
y = df['Outfit Generato']

# Pre-processing
preprocessor = ColumnTransformer(
    transformers=[
        ('tipo', OneHotEncoder(), ['Tipo Capi', 'Colore', 'Condizione', 'Stile'])
    ])

# Costruzione del modello di classificazione
model = Pipeline(steps=[
    ('preprocessor', preprocessor),
    ('classifier', RandomForestClassifier())
])

# Addestramento del modello
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)
model.fit(X_train, y_train)

# Predizione su nuovi dati
predicted_outfit = model.predict([[5, 'Blu', 'Pioggia', 'Elegante']])
print(predicted_outfit)  # Giacca + Pantaloni