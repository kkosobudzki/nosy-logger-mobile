import NosyLogger

@objc(RNNosyLogger)
class RNNosyLogger: NSObject {
    
  private let logger = NosyLogger()

  @objc
  func start(apiKey: String) -> Void {
    logger.start(apiKey: apiKey)
  }
    
  @objc
  func debug(message: String) -> Void {
    logger.debug(message)
  }
    
  @objc
  func info(message: String) -> Void {
    logger.info(message)
  }
    
  @objc
  func warning(message: String) -> Void {
    logger.warning(message)
  }
    
  @objc
  func error(message: String) -> Void {
    logger.error(message)
  }
}
