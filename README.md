# mySpringFramework

This project is simply my custom implementation of an alike spring framework in which i developed the basic annotations such as :
- Component
- Configuration
- Bean
- Qualifier
- Autowired

To process the annotations above, i created an interface called "ApplicationContext" and an implementation of that interface 
called annotationconfigApplicatiponcontext that uses the reflection API to inspect the annotations and act accordingly (like what those annotations do in spring)
- Object creation
- Dependency injection 

I included a package called beans that contains some beans annotated with the annotations Above for test. I also included a client class
for test called contextclient located in the default package


