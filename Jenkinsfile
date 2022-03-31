pipeline {
  agent {
    node {
      label 'built-in'
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
        sh 'JAVA_HOME=/var/lib/jenkins/tools/hudson.model.JDK/JDK_17 ./gradlew clean build'
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