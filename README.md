# Seguire questi passi per importare correttamente il progetto in Eclipse.

### Importazione del progetto su Eclipse
1. Aprire Eclipse (in una cartella diversa da `Progetto ReNewPhone`)
2. Selezionare `Import` dal menu File
3. Scegliere `Existing Projects into Workspace` e fare clic su *Next*
4. Navigare fino alla directory del progetto e selezionarla
5. Assicurarsi che il progetto sia spuntato nella lista dei progetti da importare
6. Fare clic su *Finish* per importare il progetto nel workspace di Eclipse

### Correggere i package
Potrebbe succedere che i package non vengano caricati correttamente, questo succede per via della cartella di source sbagliata.

1. Fare clic con il pulsante destro del mouse sul progetto in Eclipse
2. Selezionare `Build Path` e poi `Source`
3. Eliminare il path corrente (come `Progetto ReNewPhone/src`, sbagliato)
4. `Add Folder...` ed inserire `Progetto ReNewPhone/src/main/java` (corretto)

### Abilitare il refresh automatico delle cartelle di eclipse
Serve per far caricare le immagini subito.

1. Fare clic con il pulsante destro del mouse sul progetto in Eclipse
2. Selezionare `Properties` nel menu contestuale
3. Andare in `General` > `Workspace`
4. Attivare `Refresh using native hooks or polling`

### Configurazione del Classpath
Se le pagine JSP e le servlet non funzionano correttamente in Eclipse, questo succede per via del classpath errato.

1. Fare clic con il pulsante destro del mouse sul progetto in Eclipse
2. Selezionare `Properties` nel menu contestuale
3. Nella finestra di dialogo delle proprietà del progetto, selezionare `Targeted Runtimes`
4. Se non è già presente una runtime o se quella presente non funziona, fare clic su *New*
5. Selezionare Tomcat 9.0 e specificare il percorso di installazione di Tomcat
6. Fare clic su *Apply* e poi su *Close* per chiudere la finestra di dialogo
7. Fare un refresh del progetto

### Importare i file JAR necessari per il progetto
Queste sono librerie necessare per il funzionamento di certe parti.

1. Fare clic con il pulsante destro del mouse sul progetto in Eclipse
2. Selezionare `Build Path` e poi `Configure Build Path` > `Libraries`
3. Trovare i file JAR nella directory `WEB-INF` del progetto e selezionarli per l'importazione
