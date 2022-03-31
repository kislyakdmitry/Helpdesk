pipeline {
  agent any
  stages {
    stage('build') {
      agent any
      tools {
        jdk 'JDK_17'
      }
      steps {
        sh './gradlew clean build'
      }
    }

    stage('test') {
      steps {
        sh './gradle test'
        junit 'Results'
      }
    }

  }
}