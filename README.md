Как запустить тесты

  - Launch emulator or real device.
  - Run UI tests with coverage:
      - ./gradlew :app:connectedDevDebugAndroidTest
  - Aggregate coverage:
      - ./gradlew jacocoMergeAll jacocoReportAll
  - Report location:
      - HTML: build/reports/jacoco/html/index.html
      - XML: build/reports/jacoco/jacocoReportAll.xml