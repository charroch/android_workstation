# -*- encoding: utf-8 -*-
$:.push File.expand_path("../lib", __FILE__)
require "android_workstation/version"

Gem::Specification.new do |s|
  s.name        = "android_workstation"
  s.version     = AndroidWorkstation::VERSION
  s.platform    = Gem::Platform::RUBY
  s.authors     = ["Carl-Gustaf Harroch"]
  s.email       = ["carl@novoda.com"]
  s.homepage    = "https://github.com/charroch/android_workstation"
  s.summary     = %q{Utility tools to help with android development}
  s.description = %q{Android utilities to be executed within the android root project following a standard structure (apk in bin folder etc...)}

  s.rubyforge_project = "android_workstation"

  s.files         = `git ls-files`.split("\n")
  s.test_files    = `git ls-files -- {test,spec,features}/*`.split("\n")
  s.executables   = `git ls-files -- bin/*`.split("\n").map{ |f| File.basename(f) }
  s.require_paths = ["lib"]
  s.add_dependency "thor"
end
