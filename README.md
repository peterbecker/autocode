# Autocode

[![Build Status](https://travis-ci.org/peterbecker/autocode.svg?branch=master)](https://travis-ci.org/peterbecker/autocode)

Work in progress on a modular code generation framework. Works, but is expected to still change quite a bit.

General patterns:

 * the module `autocode-plugin` contains the Maven plugin that handles the code generation
 * the actual templates used to generate code are in so-called "PAKs", each has three modules:
   * the actual templates and configuration for them: `*-pak`
   * the runtime dependencies: `*-lib`
   * a test project to generate some code and validate it: `*-test`
   
The test projects show usage of the plugin. Generally speaking it comes down to:

 * add the Maven plugin to the build's `generate-sources` phase
 * add all PAKs to be used to the plugin's `dependencies`
 * add the matching runtime libraries to the build's `dependencies`
 
The PAKs are described in the `README.md` files of their main module. It is recommended to check
out the corresponding test module, including the generated code as that shows usage and scope.
