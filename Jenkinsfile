pipeline {
     agent any
     environment {
        IMAGE = "choisunguk/jenkins_springbootabord_test"
        TAG = "4.0"
        APP_LISTENING_PORT = '8080'
        cred = 'fae53adf-954a-474f-9b11-cb1c5ef93d57'
     }
     stages {
          stage("git clone") {
               steps {
                   git branch: 'main', credentialsId: '4a15af70-4918-4ec2-b918-51c796bd8959', url: 'https://github.com/choisungwook/springboot_board.git'
                    
               }
          }
          stage("compile"){
              steps {
                sh "./gradlew assemble"
              }
          }
          stage("Unit test") {
               steps {
                    sh "./gradlew test"
               }
          }
          stage("build dockerimage"){
              steps{
                  script {
                    def app = docker.build("${IMAGE}:${TAG}", "--build-arg JAR_FILE=build/libs/*.jar .")
                    // This step should not normally be used in your script. Consult the inline help for details.
                    withDockerRegistry([credentialsId: 'dockerhub', url: '']) {
                        app.push("${TAG}")
                        app.push('latest')
                    }
                    
                  }
              }
          }
     }
}
