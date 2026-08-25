# Tomcat + Maven + Git Practice App

A minimal Java web app for practicing the core DevOps loop:
**write code → commit with Git → build with Maven → deploy the WAR to Tomcat.**

It has two pages:
- `index.jsp` — a home page that shows the app version and server info
- `/count` — a servlet that counts visits in memory (resets on redeploy, so it's a quick way to confirm a fresh deploy actually happened)

---

## 1. Prerequisites

- JDK 11+
- Maven 3.6+
- Apache Tomcat 9 (running locally, e.g. on `http://localhost:8080`)

---

## 2. One-time Tomcat setup (needed for `mvn tomcat7:deploy`)

Edit `<TOMCAT_HOME>/conf/tomcat-users.xml` and add a user with deploy rights:

```xml
<role rolename="manager-script"/>
<user username="deployer" password="changeme" roles="manager-script"/>
```

Restart Tomcat after editing this file.

Then create (or edit) your **Maven** settings file at `~/.m2/settings.xml` and add a matching server entry — the `<id>` must match the `<server>` value in `pom.xml` (`tomcat-server`):

```xml
<settings>
  <servers>
    <server>
      <id>tomcat-server</id>
      <username>deployer</username>
      <password>changeme</password>
    </server>
  </servers>
</settings>
```

Never commit real credentials to Git — `settings.xml` lives outside the project on purpose.

---

## 3. Get this project under Git

```bash
cd tomcat-practice-app
git init
git add .
git commit -m "Initial commit: scaffold Maven webapp"
git tag v1.0.0
```

---

## 4. Build the WAR with Maven

```bash
mvn clean package
```

This produces `target/tomcat-practice-app.war`.

---

## 5. Deploy — three ways, in increasing order of automation

**A. Manual copy (do this first, so you understand the mechanics)**
Copy `target/tomcat-practice-app.war` into `<TOMCAT_HOME>/webapps/`. Tomcat will auto-explode and deploy it. Visit:
`http://localhost:8080/tomcat-practice-app/`

**B. Tomcat Manager web UI**
Go to `http://localhost:8080/manager/html`, log in, and upload the WAR through the "Deploy" section.

**C. Maven plugin (once A and B make sense to you)**
```bash
mvn tomcat7:deploy      # first-time deploy
mvn tomcat7:redeploy    # every deploy after that
mvn tomcat7:undeploy    # remove it
```

---

## 6. The practice loop

Repeat this cycle at least 5–10 times, making a different kind of change each time:

1. Make a change (edit `CounterServlet.java`, `index.jsp`, or `web.xml`)
2. Bump `app.version` in `web.xml`
3. `git add . && git commit -m "describe the change"`
4. `git tag vX.Y.Z`
5. `mvn clean package`
6. `mvn tomcat7:redeploy`
7. Reload the page in your browser and confirm the version number changed

Suggested changes to practice with:
- Add a new endpoint (e.g. `/hello?name=You`)
- Change the counter to reset on a schedule
- Break something on purpose, check `<TOMCAT_HOME>/logs/catalina.out`, then fix it
- Deploy `v2.0.0`, then use `git checkout v1.0.0`, rebuild, and redeploy to practice a rollback

---

## 7. Reading Tomcat logs

When a deploy fails or a page 500s, check:

```bash
tail -f <TOMCAT_HOME>/logs/catalina.out
```

Getting comfortable here — matching a stack trace back to a specific line in your servlet — is one of the most useful Tomcat skills you can build.
