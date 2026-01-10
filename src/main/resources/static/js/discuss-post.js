const urlParams = new URLSearchParams(location.search);
const postId = urlParams.get('id') || 1;

async function loadPost() {
    const res = await fetch(`/api/posts/${postId}`);
    const post = await res.json();

    document.getElementById('post-title').textContent = post.title;
    document.getElementById('post-content').textContent = post.content;
    document.getElementById('post-stats').innerHTML = `
        <span>${post.votes} votes</span>
        <span>${post.replies || 0} replies</span>
        <span>${post.views || 0} views</span>
    `;

    loadComments();
}

async function loadComments() {
    const res = await fetch(`/api/posts/${postId}/comments`);
    const comments = await res.json();
    document.getElementById('comment-count').textContent = comments.length;

    document.getElementById('comments-list').innerHTML = comments.map(c=>`
        <div style="padding:1rem;border-left:3px solid var(--primary-color);background:var(--bg-color);margin:1rem 0;border-radius:8px">
            <strong>${c.author || 'Anon'}</strong> • ${c.createdAt}
            <p>${c.content}</p>
        </div>
    `).join('');
}

async function addComment() {
    const content = document.getElementById('comment-input').value;
    await fetch(`/api/posts/${postId}/comments`, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({content, authorId: 1})
    });
    document.getElementById('comment-input').value = '';
    loadComments();
}

loadPost();
