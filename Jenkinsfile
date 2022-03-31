pipeline {
  agent {
    node {
      label 'help_desk'
    }

  }
  stages {
    stage('build') {
      agent any
      tools {
        jdk 'JDK_17'
      }
      steps {
        sh '''
java -version
echo $JAVA_HOME'''
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