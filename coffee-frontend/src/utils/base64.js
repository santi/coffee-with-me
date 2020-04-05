export default {
    encode: (string) => btoa(String.fromCharCode.apply(null, new Uint8Array(string)))
}
