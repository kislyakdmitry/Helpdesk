pipeline {
  agent {
    node {
      label 'built-in'
    }

  }
  stages {
    stage('build') {
      agent any
      steps {
        sh './gradlew clean build'
      }
    }

    stage('test') {
      steps {
        sh './gradlew test'
        junit 'Results'
      }
    }

  }
  tools {
    jdk 'JDK_17'
    gradle 'Gradle 7.3.3'
  }
}