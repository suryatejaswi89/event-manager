# eventmanager

#Bootstrap 
It is a css framework that makes the responsive web app development easy. The core idea of this framework is that any viewport can be divided into 12 columns and we tell that frame work that a container/div will extend to x number of columns out of 12. This x varies for a given device. In my development I considered the support for 3 devices. xs, sm, md. In the home page the container is divided into 2 portions targeting the medium devices and above. The for portion occupies 4 columns and second portion occupies 8 columns in the device wider than medium (797px)

/* Extra small devices (phones, less than 768px) */
/* No media query since this is the default in Bootstrap */

/* Small devices (tablets, 768px and up) */
@media (min-width: @screen-sm-min) { ... }

/* Medium devices (desktops, 992px and up) */
@media (min-width: @screen-md-min) { ... }

/* Large devices (large desktops, 1200px and up) */
@media (min-width: @screen-lg-min) { … }

@media (max-width: @screen-xs-max) { ... }
@media (min-width: @screen-sm-min) and (max-width: @screen-sm-max) { ... }
@media (min-width: @screen-md-min) and (max-width: @screen-md-max) { ... }
@media (min-width: @screen-lg-min) { … }

#AngularJS:
AngularJS is a javascript framework which lets us write client-side web applications as if we had a smarter browser. It lets us use good old HTML as our template language and lets us extend HTML’s syntax to express your application’s components clearly and succinctly. It automatically synchronizes data from our UI (view) with our JavaScript objects (model) through 2-way data binding. To help us structure your application better and make it easy to test, AngularJS teaches the browser how to do dependency injection and inversion of control.

It also helps with server-side communication, taming async callbacks with promises and deferreds, and it makes client-side navigation and deeplinking with hashbang urls or HTML5 pushState a piece of cake. Best of all? It makes the web app development easier while forcing the us to adhere to quality coding standards.