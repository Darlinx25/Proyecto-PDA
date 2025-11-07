window.addEventListener('load', () => {
    const header = document.querySelector('header');
    if (header) {
      document.body.style.paddingTop = header.offsetHeight + 'px';
    }
  });