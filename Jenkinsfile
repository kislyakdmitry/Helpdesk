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
        sh '''env | grep -e PATH -e JAVA_HOME -e GRADLE_HOME
            '''
        sh 'gradle clean build'
      }
    }

    stage('test') {
      steps {
        sh './gradle test'
        junit 'Results'
      }
    }

  }
  tools {
    jdk 'JDK_17'
  }
  environment {
    GRADLE_HOME = '/opt/gradle/latest'
    PATH = '${GRADLE_HOME}/bin:${PATH}'
  }
}