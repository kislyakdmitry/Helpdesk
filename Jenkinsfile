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
        sh './gradlew clean build --no-daemon'
      }
    }

    stage('test') {
      steps {
        sh './gradlew test --no-daemon'
        junit 'Results'
      }
    }

  }
  tools {
    jdk 'JDK_17'
    gradle 'Gradle 7.3.3'
  }
}