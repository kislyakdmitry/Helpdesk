pipeline {
  agent any
  stages {
    stage('build') {
      tools {
        jdk 'JDK_17'
      }
      steps {
        sh 'ls'
      }
    }

  }
  environment {
    PATH = '/root/jdk-17.0.2/bin;'
  }
}