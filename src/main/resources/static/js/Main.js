const API_URL = '/api/v1/1.0/ai-model-interaction';
const chatContainer = document.getElementById('chatContainer');
const emptyState = document.getElementById('emptyState');
const messageInput = document.getElementById('messageInput');
const sendBtn = document.getElementById('sendBtn');
const errorMsg = document.getElementById('errorMsg');

function autoResize(el) {
    el.style.height = 'auto';
    el.style.height = Math.min(el.scrollHeight, 140) + 'px';
}

function handleKey(e) {
    if (e.key === 'Enter' && !e.shiftKey) {
        e.preventDefault();
        sendMessage();
    }
}

function sendSuggestion(text) {
    messageInput.value = text;
    sendMessage();
}

function addMessage(text, role) {
    if (emptyState && emptyState.parentNode) {
        emptyState.remove();
    }

    const msg = document.createElement('div');
    msg.className = 'message ' + role;

    const avatar = document.createElement('div');
    avatar.className = 'avatar ' + role;
    avatar.textContent = role === 'claude' ? 'C' : 'N';

    const bubble = document.createElement('div');
    bubble.className = 'bubble';
    if (role === 'claude') {
        bubble.innerHTML = marked.parse(text);
    } else {
        bubble.textContent = text;
    }

    msg.appendChild(avatar);
    msg.appendChild(bubble);
    chatContainer.appendChild(msg);
    chatContainer.scrollTop = chatContainer.scrollHeight;
    return bubble;
}

function addThinking() {
    if (emptyState && emptyState.parentNode) emptyState.remove();

    const msg = document.createElement('div');
    msg.className = 'message claude';
    msg.id = 'thinkingMsg';

    const avatar = document.createElement('div');
    avatar.className = 'avatar claude';
    avatar.textContent = 'C';

    const bubble = document.createElement('div');
    bubble.className = 'bubble';
    bubble.innerHTML = '<div class="thinking"><span></span><span></span><span></span></div>';

    msg.appendChild(avatar);
    msg.appendChild(bubble);
    chatContainer.appendChild(msg);
    chatContainer.scrollTop = chatContainer.scrollHeight;
}

function removeThinking() {
    const el = document.getElementById('thinkingMsg');
    if (el) el.remove();
}

async function sendMessage() {
    const text = messageInput.value.trim();
    if (!text) return;

    errorMsg.innerHTML = '';
    messageInput.value = '';
    messageInput.style.height = 'auto';
    sendBtn.disabled = true;

    addMessage(text, 'user');
    addThinking();

    try {
        const response = await fetch(`${API_URL}?withModel=${model}`, {
            method: 'POST',
            headers: { 'Content-Type': 'text/plain' },
            body: text
        });

        removeThinking();

        if (!response.ok) {
            throw new Error('Server returned ' + response.status);
        }

        const reply = await response.text();
        addMessage(reply, model);

    } catch (err) {
        removeThinking();
        errorMsg.innerHTML = '<div class="error-msg">Could not reach the server. Make sure your Spring Boot app is running on port 8080.</div>';
        console.error(err);
    } finally {
        sendBtn.disabled = false;
        messageInput.focus();
    }
}