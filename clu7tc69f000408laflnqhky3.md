---
title: "Arquitectura de sistemas de pago críticos"
datePublished: Tue Mar 26 2024 03:24:16 GMT+0000 (Coordinated Universal Time)
cuid: clu7tc69f000408laflnqhky3
slug: arquitectura-de-sistemas-de-pago-criticos
cover: https://cdn.hashnode.com/res/hashnode/image/upload/v1711423054848/eff110f6-b0d4-4d82-85ca-bd7c642999f1.jpeg
tags: multi-region, arquitectura, pagos

---

---

AWS acaba de [publicar un artículo](https://aws.amazon.com/blogs/industries/architecting-critical-payment-systems-for-multi-region-resiliency/) en el que se exploran las prácticas para diseñar sistemas de pago críticos que sean altamente resilientes en entornos de múltiples regiones. Eugene Istrati, Jack Iu y Tarik Makota, autores del texto son Arquitectos de Solución en AWS para el sector financiero y destacan la importancia de la alta disponibilidad y la tolerancia a fallos en sistemas financieros, donde la interrupción del servicio puede tener consecuencias significativas.

La arquitectura propuesta se basa en la replicación de datos y distribución de cargas de trabajo en varias regiones de AWS para garantizar redundancia y recuperación ante fallas. Allí se detallan patrones de diseño como la implementación de bases de datos distribuidas y la utilización de servicios como Amazon Route 53 para la gestión de tráfico global. Se incluyen referencias a cómo los operadores de pagos pueden implementar una [arquitectura basada en eventos para recibir, consumir y publicar mensajes de pago ISO 20022.](https://aws.amazon.com/solutions/guidance/iso-20022-messaging-workflows-on-aws/)

Además, el artículo ofrece recomendaciones para la gestión de datos sensibles y el cumplimiento normativo en el contexto de sistemas de pago, destacando la importancia del cifrado de datos y el control de acceso para proteger la información confidencial.

Recomiendo la lectura del texto como una guía para arquitectos y desarrolladores que buscan construir sistemas de pagos en alta disponibilidad y con resistencia a fallos utilizando la infraestructura global de AWS.

![ISO 20022 Messaging Workflows on AWS (multi-Region)
](https://cdn.hashnode.com/res/hashnode/image/upload/v1711421098046/Tc5slQ2gm.png?auto=format align="left")

La secuencia paso a paso de los flujos de trabajo de mensajería ISO 20022 puede resumirse en:

1. Evaluación de requisitos de resiliencia: Identificar los requisitos específicos para el sistema de pago, como el tiempo de recuperación objetivo (RTO) y el punto de recuperación objetivo (RPO).
    
2. Seleccionar la estrategia de replicación de datos: Elegir entre la replicación síncrona o asíncrona de datos entre regiones de AWS, considerando factores como la latencia y la coherencia de los datos.
    
3. Diseñar la arquitectura de múlti-regiones: Utilizar servicios de AWS como Amazon Route 53, AWS Global Accelerator y Amazon DynamoDB Global Tables para diseñar una arquitectura que distribuya la carga de trabajo y los datos de manera eficiente entre múltiples regiones.
    
4. Gestión de datos sensibles: Implementar medidas de seguridad robustas para proteger los datos sensibles, como el cifrado de datos en reposo y en tránsito, y el uso de servicios de administración de claves como AWS Key Management Service (KMS).
    
5. Implementar de controles de acceso y cumplimiento normativo: Utilizar AWS Identity and Access Management (IAM) para establecer políticas de acceso granular y cumple con los requisitos regulatorios aplicables al sistema de pago.
    
6. Pruebas y monitoreo continuo: Realizar pruebas exhaustivas de resiliencia y realiza un monitoreo continuo del sistema para detectar y mitigar cualquier problema de manera proactiva.
    

En conclusión, el artículo es una guía útil para arquitectos y desarrolladores que buscan construir sistemas de pago altamente disponibles y resistentes a fallos utilizando la infraestructura global de AWS.

Fuente: [AWS for Industries](https://aws.amazon.com/blogs/industries/architecting-critical-payment-systems-for-multi-region-resiliency/) | Foto de portada: [CardMapr.nl](http://CardMapr.nl) [on Unsplash](https://unsplash.com/@cardmapr)