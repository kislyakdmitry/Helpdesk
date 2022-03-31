pipeline {
  agent any
  stages {
    stage('build') {
      agent any
      tools {
        jdk 'JDK_17'
      }
      steps {
        sh '''export PATH=/root/jdk-17.0.2/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/games:/usr/local/games:/snap/bin
export JAVA_HOME=/root/jdk-17.0.2/
export JDK_HOME=/root/jdk-17.0.2/

java -version'''
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