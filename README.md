# ERP-PSGI
Projet ERP - Licence Pro PSGI

Projet en java développé par la licence Pro PSGI - IUT Montpellier (promo 2015).

<b><i><u>SOUS ECLIPSE :</u></i></b>

<b>- Pour ajouter le projet à Eclipse :</b> Récupérer le lien HTTPS dans le GitHub.<br/>
<img width="60%" src="http://img15.hostingpics.net/pics/610018861.png">

Puis, sous Eclipse : "File" > "Import", puis créer un projet from Git :<br/>
<img width="30%" src="http://img15.hostingpics.net/pics/913545252.png">
<img width="30%" src="http://img15.hostingpics.net/pics/344595863.png">

Utilisez un clone URI en collant l'adresse que vous aviez copié toute à l'heure dans le champs URI. Tout se remplit automatiquement (vous devez mettre vos identifiants perso) :<br/>
<img width="30%" src="http://img15.hostingpics.net/pics/175289644.png">
<img width="30%" src="http://img15.hostingpics.net/pics/602874975.png">

<b>- Pour envoyer sur GitHub :</b> Clic droit sur la racine du projet > "Team" > "Commit" > Renseigner un message (obligatoire) > "Commit and push" :<br/>

<img width="30%" src="http://img15.hostingpics.net/pics/497085Capturede769cran20151126a768171105.png">
<img width="30%" src="http://img15.hostingpics.net/pics/201964Capturede769cran20151126a768171129.png">
<br/><br/>
<br/><br/>

<b>- Pour récupérer de GitHub :</b> Clic droit sur la racine du projet > "Team" > "Fetch from upstream" :<br/>

<img width="30%" src="http://img15.hostingpics.net/pics/549761911.png">
<img width="30%" src="http://img15.hostingpics.net/pics/235032272.png">

Une fois le téléchargement effectué, une petite flèche dessendante indique le nombre de commit qui ont été fait depuis le dernier téléchargement.<br/>

<img width="50%" src="http://img15.hostingpics.net/pics/758562Capturede769cran20151126a768172404.png">

Vous devez finir en faisant un "Merge" : clic droit sur la racine du projet > "Team" > "Merge" :<br/>

<img width="30%" src="http://img15.hostingpics.net/pics/686467823.png">
<img width="30%" src="http://img15.hostingpics.net/pics/458539754.png">
<img width="30%" src="http://img15.hostingpics.net/pics/221622255.png">

<br/><br/>
/!\ A savoir que : 
- Le "Commit" seul n'envoie pas sur GitHub, il faut faire un "Push to upstream". De même, pour récupérer une version, il faut d'abbord la télécharger (fetch from upstream) puis faire un "Merge".<br/>
- Si vous tentez de faire un commit alors qu'il y a des choses que vous n'avez pas téléchargées, vous devrez d'abbord récupérer la dernière version.<br/>
- Si vous tentez de faire un commit d'un fichier qui a été modifié entre temps, il y aura un conflit. GitHub bloque tout simplement et ne permet pas le "Push to upstream". Vous devrez écraser le fichier que vous avez modifié par la nouvelle version en faisant un "Pull" (clic droit > "Team" > "Pull"). Donc bien avoir en tête les modifications que vous avez faites (ou les sauvegarder quelques part sur votre PC).
