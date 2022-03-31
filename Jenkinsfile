pipeline {
  agent any
  stages {
    stage('build') {
      tools {
        jdk "JDK_17"
      }
      steps {
        sh './gradlew clean build'
      }
    }
  }
}