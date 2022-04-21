pipeline {
  agent any
  stages {
    stage('build') {
            agent{
                  docker{
                      image 'postgres:14.1'
                      reuseNode true
                    }
            }
      steps {
        sh './gradlew clean build'
      }
    }
    stage('test') {
        agent{
              docker{
                  image 'postgres:14.1'
                  reuseNode true
                }
        }
      steps {
        sh './gradlew test'
      }
    }
    stage('SonarQube Analysis') {
      steps {
        withSonarQubeEnv('Sonar') {
          sh './gradlew jacocoTestReport sonarqube -Dsonar.login=b82de7b91b30c7479f84869133f3db9e881e6e0f'
        }

      }
    }
    stage("Quality Gate") {
      steps {
        timeout(time: 2, unit: 'MINUTES') {
          waitForQualityGate abortPipeline: true
        }
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