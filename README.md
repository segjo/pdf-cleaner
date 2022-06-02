pdf-cleaner app for d.velop cloud.
![image](https://user-images.githubusercontent.com/18232114/171660143-ac0c324e-589e-4998-8234-aa6f398af18b.png)

1. download selected PDF document from d.velop documents
2. detect blank pages (no text)
3. upload document to d.velop documents as new version


## Getting Started

set SIGNATURE_SECRET as enviroment-variable. In vscode you can use .vscode/launch.json config:
```
{
    "version": "0.2.0",
    "configurations": [
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
![2022-06-02_17-07](https://user-images.githubusercontent.com/18232114/171662472-b853f8fa-205d-43e6-b3a9-0f5bc7273b2c.png)



