# Releasing

This library is available on Maven Central and published through Sonatype.
Once you have [configured your setup](#setup), execute the following to make a
release:

 - Tag the commit as a release: `git tag x.x.x`
 - Push the tags: `git push --tags`
 - Execute `./gradlew publish --max-workers=1`
 - Head over to `https://oss.sonatype.org/#stagingRepositories` and close and
   release the repository.


## Setup

You'll need to configure your setup before you can release a new version of the
library.

### Sonatype account

Create an account on https://issues.sonatype.org/secure/Signup!default.jspa.
This account will need to be authorized for publishing, which can be done by
adding a comment with the request to https://issues.sonatype.org/browse/OSSRH-59018.

Once authorized, include the username and password in your
`~/.gradle/gradle.properties` file (create the file if it does not yet exist):

```groovy
waterrower_sonatype_username=myusername
waterrower_sonatype_password=mypassword
```

### GPG Signing

Artifacts uploaded to Sonatype will need to be signed.
You'll need to generate a GPG key (see the instructions on
https://docs.github.com/en/github/authenticating-to-github/generating-a-new-gpg-key
for example).

Once generated, include the following properties in your
`~/.gradle/gradle.properties` file:

```groovy
waterrower_signing_keyId=mykeyid
waterrower_signing_password=mypassword
waterrower_signing_secretKeyRingFile=pathtomysecretkeyringfile
```

 - The `keyId` is the last 8 symbols of the public key ID. You can use `gpg -K`
   to get it.
 - The `password` is the passphrase used to protect the private key.
 - The `secretKeyRingFile` is the absolute path to the secret key ring file
   containing your private key.
   (Since gpg 2.1, you need to export the keys with command
   `gpg --keyring secring.gpg --export-secret-keys > ~/.gnupg/secring.gpg`).