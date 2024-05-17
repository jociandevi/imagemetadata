import { ImageMetadata } from 'imagemetadata';

window.testEcho = () => {
    const inputValue = document.getElementById("echoInput").value;
    ImageMetadata.echo({ value: inputValue })
}
