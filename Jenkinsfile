pipeline {
  agent any
  stages {
    stage('build') {
      agent any
      tools {
        jdk 'JDK_17'
      }
      environment {
        PATH = '/root/jdk-17.0.2/bin;/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/games:/usr/local/games:/snap/bin'
      }
      steps {
        sh 'java -version'
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
  environment {
    PATH = '/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/games:/usr/local/games:/snap/bin;/root/jdk-17.0.2/bin;'
  }
}