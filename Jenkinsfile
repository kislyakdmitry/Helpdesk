pipeline {
  tools {
    jdk 'JDK_17'
  }
  agent {
    node {
      label 'built-in'
    }
  }
  stages {
    stage('build') {
      agent any
      steps {
        sh '''
        env | grep -e PATH -e JAVA_HOME
            '''
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