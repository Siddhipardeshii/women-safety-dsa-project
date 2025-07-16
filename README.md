# women-safety-dsa-project


## 📱 Overview

Women Safety App offers a combination of security utilities including live location sharing, bulk SOS messaging, chatbot guidance, and a fake call generator — all optimized for performance using data structures and algorithms.

---

## ✨ Features

### 1. 🔐 User Authentication (Login/Register)
- **HashMap**: For in-memory credential validation.
- **SQLite**: Local storage of user credentials.
- **Firebase**: Secure cloud sync for multi-device access.
- **Emergency Contacts**: Saved during registration for fast alerting.

---

### 2. 📍 Help Centers Locator
- **Algorithm Used**: Haversine (to calculate distances between coordinates).
- **Input**: CSV file with latitude & longitude of verified help centers.
- **Output**: Displays 7 nearest help centers using Google Maps API.

---

### 3. 📡 Live Location Sharing
- Sends current GPS location via **SMS** to the 7 nearest help centers.
- Uses `LocationManager` + `SmsManager`, including Android 13+ permission fallback.

---

### 4. 📞 Fake Call Generator
- **Array-based system** that simulates a fake call or looping ringtone.
- Discreetly helps user escape threatening situations.

---

### 5. 🚨 Bulk SOS Messaging
- Uses **ArrayList** to send bulk SOS messages.
- Messages include:
  - Current live location
  - Google Maps link
  - Auto-send to emergency contacts from registration.

---

### 6. 🤖 Chatbot (Gemini API Integration)
- **Gemini API**: Handles safety-related queries (via OkHttp or Gemini SDK).
- **Features**:
  - Preloaded suggested questions (via `ArrayList`)
  - Custom query input via textbox
- **Use Cases**: FIR filing, harassment reporting, cybercrime awareness.

---

### 7. 📚 “Do You Know?” Awareness Section
- Curated safety info:
  - Emergency signals
  - Legal rights
  - Safety tips & helplines
- Displayed using `RecyclerView` for clean UI/UX.

---




