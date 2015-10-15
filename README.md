# rip-tools
Application to support user in computer activities - MS Windows orientated.

## Package
* The build process (maven-based) deploys a single folder 'deploy' which contains all files to execute the application.
* JRE is included, too.

## Features
### General
* The application provides are single window which organizes the main functions in tabs.
* The user-configuration is stored in a single file 'riptools.config', which is placed the users home-folder.

### No windows screensaver - tool
* This feature hacks the windows idle status change, to avoid start of the screensaver.
* The application scrolls the mouse wheel after a defined delay.
* User-options:
** Switch on / off
** Setup the delay intervall (in mili-seconds)
** Setup the number of mouse wheel scrolls (in pixels)
* Known bug: If feature is switched on and the screen is started anyway (e.g. manually) then this features does not work.

## Execute batch - tool
* This feature executes a command-line-batch file by mouse click.
* User-options:
** Place the name of the config-file. If there is no path, then the application expects the file available in the application folder (where the .exe file is, too).

## Text tools
* This features copies a text into the windows clipboard.
* User-options:
** Place single texts in the text fields which will be stored by the application. Each text field provides a button to copy that text to the clipboard.
