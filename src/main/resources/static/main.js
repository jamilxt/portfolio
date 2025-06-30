function copyCode() {
    const code = document.querySelector('pre code');
    if (!code) {
        alert('No code block found!');
        return;
    }

    navigator.clipboard.writeText(code.innerText).then(() => {
        alert('Code copied to clipboard!');
    }).catch(err => {
        console.error('Failed to copy code: ', err);
    });
}
