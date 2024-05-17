import { WebPlugin } from '@capacitor/core';

import type { ImageMetadataPlugin } from './definitions';

export class ImageMetadataWeb extends WebPlugin implements ImageMetadataPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
