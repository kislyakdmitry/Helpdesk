pipeline {
  agent any
  stages {
    stage('build') {
      agent any
      tools {
        jdk 'JDK_17'
      }
      steps {
        sh 'source /etc/enviroment'
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