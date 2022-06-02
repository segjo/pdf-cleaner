

pdf-cleaner app for d.velop cloud.


## Getting Started

set SIGNATURE_SECRET as enviroment-variable. In vscode you can use .vscode/launch.json config:
```
{
    "version": "0.2.0",
    "configurations": [
        {
            "type": "java",
            "name": "Launch Current File",
            "request": "launch",
            "mainClass": "${file}",
            "env": {
                "SIGNATURE_SECRET": ""
              },
        },
        {
            "type": "java",
            "name": "Launch PdfCleanerApp",
            "request": "launch",
            "mainClass": "com.dvelop.archetype.cmd.selfhosted.PdfCleanerApp",
            "projectName": "selfhosted",
            "env": {
                "SIGNATURE_SECRET": ""
              },
        }

    ]
}
```
