"use client";

export default function Footer() {
    return (
        <footer className="absolute bottom-0 flex gap-6 flex-wrap items-center justify-center p-4 bg-muted dark:text-background w-full">
            <a
                className="flex items-center gap-2 hover:underline hover:underline-offset-4"
                href=""
                target="_blank"
                rel="noopener noreferrer"
            >

                Mission
            </a>
            <a
                className="flex items-center gap-2 hover:underline hover:underline-offset-4"
                href="/#"
                target="_blank"
                rel="noopener noreferrer"
            >

                Vission
            </a>
            <a
                className="flex items-center gap-2 hover:underline hover:underline-offset-4"
                href="/#"
                target="_blank"
                rel="noopener noreferrer"
            >

                Go with us →
            </a>
        </footer>
    );
}
