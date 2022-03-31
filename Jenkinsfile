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
    PATH = '/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/games:/usr/local/games:/snap/bin;/root/jdk-17.0.2/bin;'
  }
}