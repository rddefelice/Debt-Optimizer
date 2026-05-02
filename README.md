# Debt Optimizer

**Debt Optimizer** is a modern Android application designed to help users escape the cycle of high-interest debt through mathematically optimized repayment strategies and proactive consolidation opportunities.

---

## Overview

Managing multiple loans often leads to "minimum payment fatigue," where borrowers pay significantly more in interest than necessary. Debt Optimizer automates the strategy, recommending exactly where to put every dollar to minimize interest and shorten repayment timelines.

### Key Features
*   **Optimization Engine:** Automatically calculates the most effective payment allocation (e.g., Debt Avalanche or customized weighted models).
*   **Opportunity Scanning:** Real-time notifications for eligible consolidation loans, balance-transfer offers, and settlement programs.
*   **Offline-First Design:** View and manage your loan profiles even without an internet connection.
*   **Cross-Device Support:** Adaptive layouts optimized for both Android phones and tablets (API 26+).

---

### Tech Stack

### Frontend
*   **Language:** Kotlin
*   **UI Framework:** Jetpack Compose (Declarative UI)
*   **Local Database:** Room (for secure, offline-first storage)
*   **Architecture:** Single-activity with Bottom Navigation.

### Backend (Google Firebase Ecosystem)
*   **Authentication:** Firebase Auth (Email/Password & Google Sign-In).
*   **Database:** Cloud Firestore for secure cloud syncing.
*   **Cloud Functions:** Serverless triggers for periodic consolidation scans.
*   **Notifications:** Firebase Cloud Messaging (FCM).

---

## Getting Started

### Prerequisites
*   Android Studio Jellyfish | 2023.3.1 or newer.
*   JDK 17+.
*   A Firebase project (you will need to add your own `google-services.json`).

### Installation
1. **Clone the repository:**
   ```bash
   git clone https://github.com
   ```
2. **Open in Android Studio:**
   Select `File > Open` and navigate to the cloned directory.
3. **Build & Run:**
   Sync Gradle and click the **Run** button to launch on an emulator or physical device.

---

## Roadmap
- [x] **Phase 1:** MVP (Current) - Loan input, basic optimization, and notifications.
- [x] **Phase 2:** Advanced Payment Simulators - "What-if" scenarios for extra monthly payments.
- [ ] **Phase 3:** Credit-Reporting API integration for automatic loan discovery.

---

[Project Outline](https://github.com/rddefelice/Debt-Optimizer/wiki/Debt-Optimizer-Project-Outline-Final)

---

## License
Distributed under the MIT License. See `LICENSE` for more information.
