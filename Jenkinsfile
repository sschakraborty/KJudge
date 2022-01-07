def github_repo_url = "https://github.com/sschakraborty/KJudge.git"
def project_name = "KJudge - KSystem Judge"

pipeline {
    parameters {
        string defaultValue: 'master', description: 'KJudge GitHub repository branch name', name: 'BRANCH_NAME', trim: true
    }

    agent any

    tools {
        git "git"
        maven "maven_363"
    }

    stages {
        stage("Git Checkout") {
            steps {
                echo "Checking out from ${github_repo_url} - Branch ${params.BRANCH_NAME}"
                git branch: "${params.BRANCH_NAME}", credentialsId: "2304e6ac-bdfe-4226-bc6e-b2813d5cbf54", url: "${github_repo_url}"
            }
        }

        stage("Build") {
            steps {
                echo "Building ${project_name} repository"
                sh "mvn clean install -D skipTests -T 4"
            }
        }
    }

    post {
        always {
            publishHTML([allowMissing: false, alwaysLinkToLastBuild: true, keepAll: false, reportDir: '', reportFiles: 'build_report.html', reportName: 'KJudge Build Report', reportTitles: ''])
        }
        failure {
            echo "Pipeline failed!"
            unstable "Pipeline failed - Build is unstable"
        }
        success {
            setGitHubPullRequestStatus context: '', message: 'Verified by Jenkins pipeline', state: 'SUCCESS'
        }
        unstable {
            echo "Executes when build is marked as unstable"
        }
        changed {
            echo "When there is a change in status of the pipeline compared to previous run"
        }
    }
}