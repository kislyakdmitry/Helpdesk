pipeline {
  agent any
  stages {
    stage('build') {
      agent any
      steps {
        sh './gradlew clean build --no-daemon'
      }
    }
    stage('test') {
      steps {
        sh './gradlew test --no-daemon'
        junit 'Results'
      }
    }
  }
  tools {
    jdk 'JDK_17'
  }
  post {
    always {
        junit 'build/test-results/**/*.xml'
    }
  }
}