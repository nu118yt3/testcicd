pipeline {
     agent any
     stages {
         stage('Build') {
             steps {
                 jiraComment body: 'Message from GitHub', issueKey: 'TIS-1'
             }
             post {
                 always {
                     jiraSendBuildInfo site: 'txmglobal.atlassian.net'
                 }
                 success {
                    script {
                      println "All the tests passed."
                    }
                  }
                  failure {
                      println "There are some failing tests."
                  }
             }
         }
     }
 }
