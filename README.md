Project Outline: Debt Optimizer – Android App for Optimized Loan Repayment and Consolidation
I. Project Description 
  A. Overview
    1.	Debt Optimizer is a simple, user-friendly Android mobile application designed to help individuals with multiple active loans identify the mathematically optimal repayment strategy.
    2.	Core value proposition: Automatically calculate and recommend the most effective payment allocation across loans while proactively surfacing real-world debt consolidation and settlement opportunities. 
  B. Target Audience
    1.	U.S. adults currently managing two or more loans (credit cards, personal loans, student loans, auto loans, etc.). 
  C. Project Scope
    1.	Minimum viable product (MVP) focused on loan input, optimization engine, notifications, and basic account management.
    2.	Future phases may include payment simulators and integration with credit-reporting APIs (post-MVP).
II. Problem Addressing 
  A. Market Context
    1.	77 % of U.S. citizens carry some form of debt (source: user-provided statistic).
    2.	Multiple-loan borrowers often default to minimum payments, resulting in unnecessarily high total interest being paid and extended repayment timelines. 
  B. Solution Impact
    1.	Provides personalized, data-driven repayment structures (e.g., debt avalanche, customized weighted allocation).
    2.	Actively scans and notifies users of eligible consolidation loans, balance-transfer offers, and settlement programs, directly reducing total debt burden and monthly payments.
III. Platform 
  A. Development Environment
    1.	Android Studio (latest stable version) as the primary IDE.
    2.	Language: Kotlin (preferred) with full Jetpack Compose support for modern UI. 
  B. Deployment & Ecosystem
    1.	Google Play Store distribution.
    2.	Entire backend and supporting services remain inside the Google ecosystem for seamless integration and security. 
  C. Target Devices
    1.	Android 8.0 (API 26) and above.
    2.	Optimized for both phones and tablets (adaptive layouts).
IV. Front/Back End Support 
  A. Frontend Architecture
    1.	Native Android application, built entirely in Android Studio.
    2.	UI framework: Jetpack Compose for declarative, responsive screens.
    3.	Local data persistence: Room database (offline-first loan storage). 
  B. Backend Architecture
    1.	Google Firebase as the unified backend platform. 
      a.	Firebase Authentication – user accounts, email/password, Google Sign-In. 
      b.	Cloud Firestore – secure storage of user loan profiles and calculation history. 
      c.	Firebase Cloud Messaging (FCM) – real-time notifications for consolidation opportunities. 
      d.	Firebase Functions (optional) – serverless triggers for periodic consolidation scans. 
  C. Integration & Security
    1.   All data encrypted in transit and at rest (Firebase default).
    2.	No third-party backend services outside Google ecosystem.
V. Functionality 
  A. Navigation Structure
    1.	Single Activity architecture with Bottom Navigation Bar (three persistent tabs). 
  B. Tab 1 – Home (Default Screen)
    1.   Displays recommended repayment structure summary (total interest saved, payoff       timeline, monthly allocation per loan).
    2.	Live notification feed for new consolidation/settlement offers.
    3.	Quick-action buttons: “Apply Now” (deep link to partner offers) and “Recalculate.” 
  C. Tab 2 – Debt Inputs
    1.	Add/Edit/Delete individual loans.
    2.	Required fields per loan: 
      a. Loan nickname 
      b. Current principal balance 
      c. Annual interest rate (APR) 
      d. Remaining term (months) 
      e. Minimum monthly payment 
      f. Optional: loan type, lender name, fixed/variable rate flag.
  D. Tab 3 – Settings
    1.	User account management: 
      a. Update email, password, phone number 
      b. Profile picture upload
    2.	Preferences: notification toggles, currency format, repayment strategy priority (avalanche vs. custom).
    3.	Data export, account deletion, and support links. 
  E. Core Engine (Background)
    1.	Optimization algorithm runs locally on device (Kotlin) and/or via Firebase Functions.
    2.	Real-time recalculation on any loan change.
    3.	Consolidation scanner (Firebase scheduled function) matches user profile against aggregated partner offers.
 
