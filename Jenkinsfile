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
        sh './gradlew clean build -Dorg.gradle.java.home=/var/lib/jenkins/tools/hudson.model.JDK/JDK_17'
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