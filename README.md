We want to create a simple phonebook application where our users can create contacts and save
associated phone numbers. The service should be as accessible to as many existing tools as possible.
Product Management has done extensive research and has decided upon the following requirements:

### User Stories
As a user I want to create a contact
As a user I want my contact to have a name
As a user I want to edit the name associated with a contact
As a user I want to add one or more phone numbers to a contact
As a user I want delete a contact
As a user I want to get a list of all of my contacts
As a user I want to authenticate with a username and password
As a user I want to prevent other people from accessing my contacts
As a PM I don't want users to lose their contacts when a service is restarted

### Business Rules
One contact can have 0 or more phone numbers
More than one contact can have the same phone number (think household landlines)
We support all international phone number formats.


We have created the following REST API specification for the features described above.
The full API specification can be found on Apiary. http://docs.cwcontacts.apiary.io/#
Using your preferred language write the backend service that implements the API specification.


###Technical Notes
* You can hardcode a few users and passwords, but still follow basic security principles. If you have time you can add a 'register' call
* Your service should be runnable with a single command line call
* Consider appropriate testing and source control
* The data must survive a service restart


### USAGE
java -jar phonebook-1.0.jar server config.yaml

* HTTPS connection is used on port 8080
* three resources available

#### /user
* POST json object as described on Apiary and receive token. Token is live for 15 minutes

#### /register
* POST json object that describes username and password and receive id

#### /contacts - as described on Apiary
