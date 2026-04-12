Debt Optimizer – Android App for Optimized Loan Repayment and Consolidation

I. Project Description

A. Overview
  Debt Optimizer is a simple, user‑friendly Android mobile application designed to help individuals with multiple active loans identify the mathematically optimal repayment strategy.
  Core value proposition: Automatically calculate and recommend the most effective payment allocation across loans while proactively surfacing real‑world debt consolidation and settlement opportunities.

B. Target Audience
  U.S. adults currently managing two or more loans (credit cards, personal loans, student loans, auto loans, etc.).

C. Project Scope
  Minimum viable product (MVP) focused on loan input, optimization engine, notifications, and basic account management.
  Future phases may include payment simulators and integration with credit‑reporting APIs (post‑MVP).

II. Problem Addressing
A. Market Context
  77% of U.S. citizens carry some form of debt.
  Multiple‑loan borrowers often default to minimum payments, resulting in unnecessarily high total interest paid and extended repayment timelines.

B. Solution Impact
  Provides personalized, data‑driven repayment structures (e.g., debt avalanche, customized weighted allocation).
  Actively scans and notifies users of eligible consolidation loans, balance‑transfer offers, and settlement programs, reducing total debt burden and monthly payments.

III. Platform
A. Development Environment
  Android Studio (latest stable version) as the primary IDE.
  Language: Kotlin (preferred) with full Jetpack Compose support for modern UI.

B. Deployment & Ecosystem
  Google Play Store distribution.
  Backend and services remain inside the Google ecosystem for seamless integration and security.

C. Target Devices
  Android 8.0 (API 26) and above.
  Optimized for both phones and tablets (adaptive layouts).

IV. Front/Back End Support
A. Frontend Architecture
  Native Android app built in Android Studio.
  Jetpack Compose for declarative UI.
  Room database for offline‑first loan storage.

B. Backend Architecture
  The unified backend platform is Google Firebase:
    Firebase Authentication – user accounts, email/password, Google Sign‑In
    Cloud Firestore – secure storage of loan profiles and calculation history
    Firebase Cloud Messaging (FCM) – real‑time notifications for consolidation opportunities
    Firebase Functions – optional serverless triggers for periodic consolidation scans

C. Integration & Security
  All data encrypted in transit and at rest (Firebase default).
  No third‑party backend services outside the Google ecosystem.

V. Functionality
A. Navigation Structure
  Single‑activity architecture with a Bottom Navigation Bar (three persistent tabs).

B. Tab 1 – Home (Default Screen)
  Displays recommended repayment structure (interest saved, payoff timeline, loan‑by‑loan allocation).
  Live notification feed for consolidation and settlement offers.
  Quick‑action buttons: Apply Now and Recalculate.

C. Tab 2 – Debt Inputs
  Add, edit, or delete individual loans.
  Required fields per loan:
    Loan nickname
    Current principal balance
    Annual interest rate (APR)
    Remaining term (months)
    Minimum monthly payment
    Optional fields: loan type, lender name, fixed/variable rate flag

D. Tab 3 – Settings
  User account management (update email, password, phone number)
  Profile picture upload
  Preferences: notification toggles, currency format, repayment strategy priority
  Data export, account deletion, support links

E. Core Engine (Background)
  Optimization algorithm runs locally and/or via Firebase Functions.
  Recalculates automatically after any loan change.
  Consolidation scanner compares user profiles against partner offers.

VI. Versioning / Chnages
  April 12, 2026: Initial code commit for code base. Google Backend Firebase services configured for auth. Working on UI in next phase of code changes.
    -  Architectural framework (Data, Domain, Dependency, and UI layers) established. Login screen with account sign-up buttons complete. Initial landing screen once logged in established. Adding additional app screens in        future updates.
