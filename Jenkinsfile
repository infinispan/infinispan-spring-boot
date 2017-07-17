#!/usr/bin/env groovy

pipeline {
    agent any
    stages {
        stage('SCM Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                script {
                    def mvnHome = tool 'Maven'
                    sh "${mvnHome}/bin/mvn clean install -T2.0C -Dmaven.test.failure.ignore=true"
                    junit '**/target/*-reports/*.xml'
                }
            }
        }


        stage('Deploy SNAPSHOT') {
            when {
                branch 'master'
            }
            steps {
                configFileProvider([configFile(fileId: 'maven-settings-with-deploy-snapshot', variable: 'MAVEN_SETTINGS')]) {
                    script {
                        def mvnHome = tool 'Maven'
                        sh "${mvnHome}/bin/mvn deploy -s $MAVEN_SETTINGS -DskipTests"
                    }
                }
            }
        }

        stage('Release') {
            when {
                branch 'master'
            }
            steps {
                timeout(time:1, unit:'DAYS') {
                    script {
                        milestone()
                        try {
                            def userInput = input message: 'Start the release?', ok: 'Start release', parameters:
                                    [booleanParam(defaultValue: true, description: 'Dry run', name: 'dry_run'),
                                     string(defaultValue: '1.0.0-SNAPSHOT', description: 'New development version', name: 'version_new_dev'),
                                     string(defaultValue: '1.0.0.Alpha1', description: 'Release version', name: 'version_release')]
                            configFileProvider([configFile(fileId: 'maven-settings-with-deploy-release', variable: 'MAVEN_SETTINGS')]) {
                                def dry_run = userInput['dry_run']
                                def version_new_dev = userInput['version_new_dev']
                                def version_release = userInput['version_release']
                                def mvnHome = tool 'Maven'
                                sh "${mvnHome}/bin/mvn --batch-mode -s $MAVEN_SETTINGS release:prepare release:perform -DdevelopmentVersion=${version_new_dev} -DreleaseVersion=${version_release} -Dtag=${version_release} -DdryRun=${dry_run}"
                            }
                        } catch(err) { // timeout reached or input false
                            echo "Aborted..."
                        }
                    }
                }
            }
        }
    }
}
