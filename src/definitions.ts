export interface ImageMetadataPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
