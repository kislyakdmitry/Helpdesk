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
        emailext(
        subject: "Jenkins: ${env.JOB_NAME} ${env.BUILD_NUMBER} - ${currentBuild.currentResult}",
        body: """<p>Build status is ${currentBuild.currentResult}. Check console output at <a href='${env.BUILD_URL}'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>;</p>""",
        recipientProviders: [buildUser(), developers(), requestor()],
        )
        junit 'build/test-results/**/*.xml'
      }
    }
  tools {
    jdk 'JDK_17_new'
  }
}