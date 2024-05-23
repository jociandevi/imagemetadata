import { WebPlugin } from '@capacitor/core';

import type { ImageMetadataPlugin } from './definitions';

export class ImageMetadataWeb extends WebPlugin implements ImageMetadataPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }

  async getImagesBetweenDates(options: {
    validFrom: number;
    validTo: number;
  }): Promise<{ imagePaths: string[] }> {
    console.log('getImagesBetweenDates', options);
    return { imagePaths: [] };
  }

  async getMetadata(options: {
    filePath: string;
  }): Promise<{ creationDate: number }> {
    console.log('getMetadata', options);
    return { creationDate: Date.now() };
  }

  async logCrashlyticsMessage(options: { message: string }): Promise<void> {
    console.log('logCrashlyticsMessage', options);
  }
}
