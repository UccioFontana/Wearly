1. Fase di Filtro Iniziale (Basato su regole)

Questa fase utilizza logiche esplicite per eliminare capi o combinazioni che non rispettano determinati vincoli.

Input:
	•	Guadaroba dell’utente: Una lista di capi con i relativi attributi (es. category, style, material, color, etc.).
	•	Condizioni esterne: Parametri come temperatura e condizioni meteorologiche.
	•	Preferenze dell’utente: Lo stile desiderato (es. casual, elegante, sportivo).

Output:
	•	Una lista filtrata di capi, organizzata per categoria (top, bottom, shoes, etc.).

2. Fase di Valutazione e Scoring (Basata su Machine Learning)

Questa fase utilizza un modello di machine learning per valutare le combinazioni di capi filtrati, assegnando un punteggio ad ogni outfit.

Input:
	•	Combinazioni di outfit (es. un insieme di top, bottom, e shoes) generate dalla fase precedente.
	•	Attributi dei capi all’interno di ogni combinazione (es. style, color, material, category).

Output:
	•	Una lista ordinata di outfit con i relativi punteggi, da cui il sistema può scegliere i migliori.

Flusso Dettagliato
	1.	Pre-elaborazione dei capi:
        •	Si codificano i dati categoriali (es. category, style, material) in numeri tramite tecniche come Label Encoding.
        •	Si calcolano metriche derivate come color_code (ad esempio, una rappresentazione HSV o RGB semplificata).
    2.	Filtro iniziale:
        •	Escludi capi basati su logiche come:
        •	Condizioni meteo: Escludi tessuti non adatti a pioggia o temperature estreme.
        •	Categorie necessarie: Se manca una categoria obbligatoria (es. scarpe), scarta le combinazioni.
        •	Genera una lista ridotta di outfit plausibili.
	3.	Valutazione degli outfit:
        •	Per ogni combinazione generata, il modello ML assegna uno score.
        •	Lo score riflette aspetti come:
        •	Complementarità dei colori: Il modello apprende quali colori funzionano bene insieme.
        •	Consistenza dello stile: Il modello può favorire outfit con capi dello stesso stile.
        •	Stagionalità o contesto: Puoi incorporare preferenze implicite, come combinazioni specifiche per determinati periodi dell’anno.
	4.	Selezione finale:
	    •	Ordina gli outfit in base allo score e restituisci i migliori N (ad esempio, i primi 3 outfit più rilevanti).

Composizione del Dataset

Per allenare il modello ML della fase 2, hai bisogno di un dataset che rappresenti esempi di outfit e i relativi punteggi. Ecco come strutturarlo:

Struttura del Dataset

Ogni riga rappresenta un outfit, con le seguenti colonne:
	•	Attributi dei capi:
        •	top_category, top_material, top_color_code, top_style
        •	bottom_category, bottom_material, bottom_color_code, bottom_style
        •	shoes_category, shoes_material, shoes_color_code, shoes_style
	•	Condizioni esterne:
        •	temperature (numerico, ad esempio 15°C)
        •	weather_condition (categorico, es. “rain”, “sunny”, “snow”)
	•	Target (label):
        •	score (numerico): Un punteggio rappresentativo della qualità dell’outfit.

Esempio

top_category	top_material	top_color_code	bottom_category	bottom_material	bottom_color_code	shoes_category	shoes_material	shoes_color_code	temperature	weather_condition	score
t-shirt	cotton	0.5	jeans	denim	0.3	sneakers	leather	0.4	20	sunny	8.5
sweater	wool	0.7	trousers	wool	0.8	boots	leather	0.5	5	rain	9.0

Architettura del Modello ML
	1.	Input Features:
        •	Attributi codificati dei capi per ogni categoria (top, bottom, shoes).
        •	Condizioni esterne (es. temperature, weather_condition).
	2.	Output:
        •	Predizione di uno score numerico per ogni combinazione di outfit.
	3.	Modello consigliato:
        •	Random Forest Regressor:
        •	Ideale per dataset di medie dimensioni e output continuo come uno score.
        •	Interpretabile grazie alla possibilità di analizzare l’importanza delle feature.
        •	Alternative avanzate:
        •	XGBoost o LightGBM: Più efficienti su dataset più grandi.
        •	Deep Learning (opzionale): In caso di dati abbondanti e complessi, reti neurali che combinano embedding per categorie e metriche continue.

Vantaggi di questo approccio
	1.	Flessibilità: Puoi aggiornare le regole della fase 1 senza impattare il modello ML.
	2.	Scalabilità: Il modello ML analizza solo un sottoinsieme di combinazioni, riducendo il carico computazionale.
	3.	Capacità predittiva: Il modello apprende relazioni complesse tra colori, stili e materiali, migliorando la qualità delle raccomandazioni nel tempo.

Con questo approccio, offri un sistema bilanciato tra controllo esplicito e capacità di apprendimento.





curl -X POST http://127.0.0.1:5000/generate_outfit \
-H "Content-Type: application/json" \
-d '{
  "user_items": {
    "tops": [
      {
        "top_category": "Hoodie",
        "top_material": "Cotton",
        "top_color": [120, 30, 50],
        "top_color_code": 60.24
      },
      {
        "top_category": "Blazer",
        "top_material": "Wool",
        "top_color": [255, 200, 100],
        "top_color_code": 201.87
      },
      {
        "top_category": "T-shirt",
        "top_material": "Cotton",
        "top_color": [0, 0, 255],
        "top_color_code": 240.00
      },
      {
        "top_category": "Sweater",
        "top_material": "Wool",
        "top_color": [255, 0, 0],
        "top_color_code": 0.00
      },
      {
        "top_category": "Jacket",
        "top_material": "Leather",
        "top_color": [0, 0, 0],
        "top_color_code": 0.00
      },
      {
        "top_category": "Coat",
        "top_material": "Wool",
        "top_color": [192, 192, 192],
        "top_color_code": 168.00
      },
      {
        "top_category": "Cardigan",
        "top_material": "Cotton",
        "top_color": [255, 255, 255],
        "top_color_code": 0.00
      }
    ],
    "bottoms": [
      {
        "bottom_category": "Jeans",
        "bottom_material": "Denim",
        "bottom_color": [50, 50, 200],
        "bottom_color_code": 85.76
      },
      {
        "bottom_category": "Shorts",
        "bottom_material": "Cotton",
        "bottom_color": [200, 100, 50],
        "bottom_color_code": 132.54
      },
      {
        "bottom_category": "Trousers",
        "bottom_material": "Wool",
        "bottom_color": [0, 0, 0],
        "bottom_color_code": 0.00
      },
      {
        "bottom_category": "Sweatpants",
        "bottom_material": "Fleece",
        "bottom_color": [255, 255, 0],
        "bottom_color_code": 60.00
      },
      {
        "bottom_category": "Leggings",
        "bottom_material": "Spandex",
        "bottom_color": [255, 100, 100],
        "bottom_color_code": 345.00
      },
      {
        "bottom_category": "Cargo Pants",
        "bottom_material": "Cotton",
        "bottom_color": [100, 200, 100],
        "bottom_color_code": 120.00
      },
      {
        "bottom_category": "Chinos",
        "bottom_material": "Cotton",
        "bottom_color": [200, 200, 200],
        "bottom_color_code": 200.00
      }
    ],
    "shoes": [
      {
        "shoes_category": "Boots",
        "shoes_material": "Leather",
        "shoes_color": [40, 40, 40],
        "shoes_color_code": 40.00
      },
      {
        "shoes_category": "Sneakers",
        "shoes_material": "Mesh",
        "shoes_color": [255, 255, 255],
        "shoes_color_code": 255.00
      },
      {
        "shoes_category": "Loafers",
        "shoes_material": "Leather",
        "shoes_color": [0, 0, 0],
        "shoes_color_code": 0.00
      },
      {
        "shoes_category": "Heels",
        "shoes_material": "Suede",
        "shoes_color": [255, 0, 255],
        "shoes_color_code": 300.00
      },
      {
        "shoes_category": "Sandals",
        "shoes_material": "Leather",
        "shoes_color": [255, 200, 100],
        "shoes_color_code": 38.00
      },
      {
        "shoes_category": "Flats",
        "shoes_material": "Canvas",
        "shoes_color": [150, 75, 0],
        "shoes_color_code": 25.00
      },
      {
        "shoes_category": "Running Shoes",
        "shoes_material": "Mesh",
        "shoes_color": [0, 255, 0],
        "shoes_color_code": 120.00
      }
    ]
  },
  "temperature": 15,
  "weather_condition": "Cloudy"
}'