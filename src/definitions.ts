export interface ImageMetadataPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
  getImagesBetweenDates(options: {
    validFrom: number;
    validTo: number;
  }): Promise<{ imagePaths: string[] }>;
  getMetadata(options: { filePath: string }): Promise<{ creationDate: number }>;
  logCrashlyticsMessage(options: { message: string }): Promise<void>;
}
