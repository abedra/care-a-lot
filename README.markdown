## This project is no longer under active development

# Care a Lot

Do you have a lot of open source projects on Github? Do you ever
wonder how much you or other people care about those projects? Care a
Lot will give you a high level view of all your public projects and
show you how much you care by telling you how long ago you committed
to a project and how much others care by telling you how many people
are watching your project, how many issues are open against it, and
how many people have forked it.

## Usage

    $ lein deps
    $ lein run

## TODO

Along with helping to manage open source projects, this will be an
example app for the compojure web framework. There are a lot of things
to do to make this app useful. Here is the short list:

* Make ajax calls for the issues and last commit so the page load doesn't make n*2 calls to the github api before the page has a chance to load
* Color rows that you don't "Care" about. If you haven't pushed in over 6 months, or if nobody is watching your project.
* Add more of a UI. Care a Lot has rainbows and bears doesn't it?
