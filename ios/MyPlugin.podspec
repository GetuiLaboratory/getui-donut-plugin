Pod::Spec.new do |spec|
  spec.name         = 'MyPlugin'
  spec.version      = '1.0.0'
  spec.summary      = 'Summary of MyPlugin'
  spec.homepage     = 'https://your-framework-website.com'
  spec.author       = { 'Your Name' => 'your@email.com' }
  spec.source       = { :git => 'https://github.com/your/repo.git', :tag => "#{spec.version}" }

  # Set your deployment target
  spec.ios.deployment_target = '8.0'
  

  spec.dependency 'GTSDK'

end