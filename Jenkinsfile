pipeline {
  agent any
  stages {
    stage('build') {
      steps {
        sh './gradlew clean build'
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

  post {
      always {
        emailext body: '${env.BUILD_URL} has result ${currentBuild.result}',
        recipientProviders: [
                    [ $class: "DevelopersRecipientProvider" ],
                    [ $class: "RequesterRecipientProvider" ],
                    [ $class: "CulpritsRecipientProvider"]
                ]
        , subject: 'build'
        junit 'build/test-results/**/*.xml'
      }
    }
  tools {
    jdk 'JDK_17_new'
  }
}